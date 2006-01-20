package com.intellij.ide.actions;

import com.intellij.ide.IdeBundle;
import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.util.Icons;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * The standard "New Class" action.
 *
 * @since 5.1
 */
public class CreateClassAction extends CreateElementActionBase {
  public CreateClassAction() {
    super(IdeBundle.message("action.create.new.class"),
          IdeBundle.message("action.create.new.class"), Icons.CLASS_ICON);
  }

  @NotNull
  protected PsiElement[] invokeDialog(Project project, PsiDirectory directory) {
    CreateElementActionBase.MyInputValidator validator = new CreateElementActionBase.MyInputValidator(project, directory);
    Messages.showInputDialog(project, IdeBundle.message("prompt.enter.new.class.name"),
                             IdeBundle.message("title.new.class"), Messages.getQuestionIcon(), "", validator);
    return validator.getCreatedElements();
  }

  protected String getCommandName() {
    return IdeBundle.message("command.create.class");
  }

  protected void checkBeforeCreate(String newName, PsiDirectory directory) throws IncorrectOperationException {
    directory.checkCreateClass(newName);
  }

  protected String getErrorTitle() {
    return IdeBundle.message("title.cannot.create.class");
  }

  public void update(AnActionEvent e) {
    super.update(e);
    DataContext dataContext = e.getDataContext();
    Project project = (Project)dataContext.getData(DataConstants.PROJECT);
    Presentation presentation = e.getPresentation();
    if (presentation.isEnabled()) {
      IdeView view = (IdeView)dataContext.getData(DataConstants.IDE_VIEW);
      assert view != null;
      ProjectFileIndex projectFileIndex = ProjectRootManager.getInstance(project).getFileIndex();
      PsiDirectory[] dirs = view.getDirectories();
      for (PsiDirectory dir : dirs) {
        if (projectFileIndex.isInSourceContent(dir.getVirtualFile()) && dir.getPackage() != null) {
          return;
        }
      }

      presentation.setEnabled(false);
      presentation.setVisible(false);
    }
  }

  protected String getActionName(PsiDirectory directory, String newName) {
    return IdeBundle.message("progress.creating.class", directory.getPackage().getQualifiedName(), newName);
  }

  @NotNull
  protected PsiElement[] create(String newName, PsiDirectory directory) throws IncorrectOperationException {
    return new PsiElement[]{directory.createClass(newName)};
  }
}
