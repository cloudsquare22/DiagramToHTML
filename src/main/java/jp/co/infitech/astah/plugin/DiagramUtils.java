package jp.co.infitech.astah.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IActivityDiagram;
import com.change_vision.jude.api.inf.model.IClassDiagram;
import com.change_vision.jude.api.inf.model.ICommunicationDiagram;
import com.change_vision.jude.api.inf.model.IComponentDiagram;
import com.change_vision.jude.api.inf.model.ICompositeStructureDiagram;
import com.change_vision.jude.api.inf.model.IDataFlowDiagram;
import com.change_vision.jude.api.inf.model.IDeploymentDiagram;
import com.change_vision.jude.api.inf.model.IERDiagram;
import com.change_vision.jude.api.inf.model.IERModel;
import com.change_vision.jude.api.inf.model.IMatrixDiagram;
import com.change_vision.jude.api.inf.model.IMindMapDiagram;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.model.IRequirementDiagram;
import com.change_vision.jude.api.inf.model.IRequirementTable;
import com.change_vision.jude.api.inf.model.ISequenceDiagram;
import com.change_vision.jude.api.inf.model.IStateMachineDiagram;
import com.change_vision.jude.api.inf.model.ISubsystem;
import com.change_vision.jude.api.inf.model.IUseCaseDiagram;
import com.change_vision.jude.api.inf.project.ProjectAccessor;

public class DiagramUtils {
    static public List<INamedElement> getAllINamedElement(ProjectAccessor projectAccessor) throws ProjectNotFoundException {
        List<INamedElement> result = new ArrayList<>();
        result.addAll(Arrays.asList(projectAccessor.findElements(IActivityDiagram.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(IClassDiagram.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(IUseCaseDiagram.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(ICommunicationDiagram.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(IComponentDiagram.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(ICompositeStructureDiagram.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(IDataFlowDiagram.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(IDeploymentDiagram.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(IERDiagram.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(IMatrixDiagram.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(IMindMapDiagram.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(IRequirementDiagram.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(IRequirementTable.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(ISequenceDiagram.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(IStateMachineDiagram.class)));

        result.addAll(Arrays.asList(projectAccessor.findElements(IPackage.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(IERModel.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(IModel.class)));
        result.addAll(Arrays.asList(projectAccessor.findElements(ISubsystem.class)));

        return result;
    }

    static public boolean isDiagram(INamedElement iNamedElement) {
        boolean result = true;
        if((iNamedElement instanceof IPackage) == true) {
            result = false;
        }
        else if(iNamedElement instanceof IERModel) {
            result = false;
        }
        else if(iNamedElement instanceof IModel) {
            result = false;
        }
        else if(iNamedElement instanceof ISubsystem) {
            result = false;
        }
        return result;
    }

}
