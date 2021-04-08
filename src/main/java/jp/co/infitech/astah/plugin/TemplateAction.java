package jp.co.infitech.astah.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IERDiagram;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;

public class TemplateAction implements IPluginActionDelegate {
    private IModel project = null;
    private String outputDirectory = "";

    public Object run(IWindow window) throws UnExpectedException {
        try {
            AstahAPI api = AstahAPI.getAstahAPI();
            ProjectAccessor projectAccessor = api.getProjectAccessor();
            project = projectAccessor.getProject();

            List<INamedElement> iNamedElementList = DiagramUtils.getAllINamedElement(projectAccessor);
            if(iNamedElementList.isEmpty() == false) {
                String sourceTopFolder = null;
                JFileChooser jFilechooser = new JFileChooser(sourceTopFolder);
                jFilechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jFilechooser.setDialogTitle("Output Folder Selection");
                jFilechooser.setApproveButtonText("Select");
                int selected = jFilechooser.showOpenDialog(null);
                if(selected == JFileChooser.APPROVE_OPTION) {
                    outputDirectory = jFilechooser.getSelectedFile().getPath();
                    iNamedElementList.forEach(ie -> {
                        try {
                            if(DiagramUtils.isDiagram(ie) == true) {
                                ((IDiagram)ie).exportImage(outputDirectory, "png", 96);
                            }
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    });

                    makeHTML(iNamedElementList);
                    JOptionPane.showMessageDialog(window.getParent(), "Successfully exported.", "Message", JOptionPane.PLAIN_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(window.getParent(), "No diagram.", "Alert", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (ProjectNotFoundException e) {
            String message = "Project is not opened.Please open the project or create new project.";
            JOptionPane.showMessageDialog(window.getParent(), message, "Warning", JOptionPane.WARNING_MESSAGE);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(window.getParent(), "Unexpected error has occurred.", "Alert", JOptionPane.ERROR_MESSAGE);
            throw new UnExpectedException();
        }
        return null;
    }

    void makeHTML(List<INamedElement> iNamedElementList) throws IOException {
        Collections.sort(iNamedElementList, new Comparator<INamedElement>() {
            @Override
            public int compare(INamedElement o1, INamedElement o2) {
                int result = o1.getFullName("/").compareTo(o2.getFullName("/"));
                return result;
            }
        });

        List<String> html = new ArrayList<>();
        html.add("<!DOCTYPE html>");
        html.add("<html>");
        html.add("<head>");
        html.add("<meta charset=\"UTF-8\">");
        html.add("<title>" + project.getName() + "</title>");
        html.add("</head>");
        html.add("<body>");
        html.add("<h1>" + project.getName() + "</h1>");

        iNamedElementList.forEach(ie -> {
            if(DiagramUtils.isDiagram(ie) == true) {
                String name = ie.getName();
                if(name.contains("/") == true) {
                    System.out.println("@/@:" + name);
                    name = name.replaceAll("/", "_");
                }
                String fullName = ie.getFullNamespace("/") + "/" + name;
                System.out.println("@fullName@:" + fullName);
                if(ie instanceof IERDiagram) {
                    File file = new File("./Default Schema" + fullName + ".png");
                    if(file.exists() == false) {
                        System.out.println("@Default Schema/@:" + name);
                        fullName = fullName.replaceAll("/Default Schema", "");
                    }
                }
                html.add("<a href=\"./" + fullName + ".png\" target=\"" + ie.getName() + "\">" + ie.getFullName("/") + "</a><br />");
            }
//			else {
//				html.add("<pre>" + ie.getFullName("/") + "</pre><br />");
//			}
        });

        html.add("</body>");
        html.add("</html>");

        Path path = Paths.get(outputDirectory, "index.html");
        Files.write(path, html, StandardCharsets.UTF_8, StandardOpenOption.CREATE);

//		html.forEach(System.out::println);
    }

}
