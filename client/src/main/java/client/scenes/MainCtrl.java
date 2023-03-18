/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {
    private Stage primaryStage;

    private WorkspaceCtrl workspaceCtrl;
    private Scene workspaceScene;

    private BoardCtrl boardCtrl;
    private Parent boardRoot; // Not a scene as it's to be embedded within the workspaceScene.

    public void initialize(
            Stage primaryStage,
            Pair<WorkspaceCtrl, Parent> workspace,
            Pair<BoardCtrl, Parent> board
    ) {
        this.primaryStage = primaryStage;

        this.workspaceCtrl = workspace.getKey();
        this.workspaceScene = new Scene(workspace.getValue());
        this.boardCtrl = board.getKey();
        this.boardRoot = board.getValue();

        primaryStage.setTitle("Talio");
        primaryStage.setScene(workspaceScene);

        workspaceCtrl.setBoardView(boardRoot);
        boardCtrl.refresh();

        primaryStage.show();
    }

    /**
     * Set active board ID for the client to render.
     * Refer to {@link BoardCtrl#setBoard(String)}
     * @param boardID board to set globally.
     * @see BoardCtrl#setBoard(String)
     */
    public void setBoard(String boardID) {
        this.boardCtrl.setBoard(boardID);
    }

    /**
     * Get the boardID being rendered by the application and the Board scene.
     * Refer to {@link BoardCtrl#getBoard()}
     * @return the globally active boardID.
     * @see BoardCtrl#getBoard()
     */
    public String getBoard() {
        return this.boardCtrl.getBoard();
    }
}
