package com.intellij.openapi.vcs.checkin;

import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.ui.RefreshableOnComponent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A callback which can be used to extend the user interface of the Checkin Project/Checkin File
 * dialogs and to perform actions before commit, on successful commit and on failed commit.
 *
 * @author lesya
 * @since 5.1
 * @see CheckinHandlerFactory#createHandler(com.intellij.openapi.vcs.CheckinProjectPanel)
 * @see CodeAnalysisBeforeCheckinHandler
 */
public abstract class CheckinHandler {

  public enum ReturnResult {
    COMMIT, CANCEL, CLOSE_WINDOW
  }

  /**
   * Returns the panel which is inserted in the "Before Check In" group box of the Checkin Project
   * or Checkin File dialogs.
   *
   * @return the panel instance, or null if the handler does not provide any options to show in the
   * "Before Check In" group.
   */
  @Nullable
  public RefreshableOnComponent getBeforeCheckinConfigurationPanel() {
    return null;
  }

  /**
   * Returns the panel which is inserted in the "After Check In" group box of the Checkin Project
   * or Checkin File dialogs.
   *
   * @return the panel instance, or null if the handler does not provide any options to show in the
   * "After Check In" group.
   */
  @Nullable
  public RefreshableOnComponent getAfterCheckinConfigurationPanel() {
    return null;
  }

  /**
   * Performs the before check-in processing. The method can use the
   * {@link com.intellij.openapi.vcs.CheckinProjectPanel} instance passed to
   * {@link CheckinHandlerFactory#createHandler(com.intellij.openapi.vcs.CheckinProjectPanel)} to
   * get information about the files to be checked in.
   *
   * @return the code indicating whether the check-in operation should be performed or aborted.
   */
  public ReturnResult beforeCheckin() {
    return ReturnResult.COMMIT;
  }

  /**
   * Performs the processing on successful check-in. The method can use the
   * {@link com.intellij.openapi.vcs.CheckinProjectPanel} instance passed to
   * {@link CheckinHandlerFactory#createHandler(com.intellij.openapi.vcs.CheckinProjectPanel)} to
   * get information about the checked in files.
   */
  public void checkinSuccessful() {

  }

  /**
   * Performs the processing on failed check-in. The method can use the
   * {@link com.intellij.openapi.vcs.CheckinProjectPanel} instance passed to
   * {@link CheckinHandlerFactory#createHandler(com.intellij.openapi.vcs.CheckinProjectPanel)} to
   * get information about the checked in files.
   *
   * @param exception the list of VCS exceptions identifying the problems that occurred during the
   * commit operation.
   */
  public void checkinFailed(List<VcsException> exception) {

  }
}
