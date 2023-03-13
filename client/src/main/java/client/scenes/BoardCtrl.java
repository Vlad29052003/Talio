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

import java.net.URL;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class BoardCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private String boardID = "";

    @FXML
    private Text boardText;

    @Inject
    public BoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Set the ID of the board to be rendered by the view.
     * @param board the board which the board view is to render.
     */
    public void setBoard(String board) {
        this.boardID = board;
        this.refresh();
    }

    /**
     * Get the boardID currently being rendered by the Board scene.
     * @return the boardID currently being rendered.
     */
    public String getBoard() {
        return this.boardID;
    }

    /**
     * Refresh the Board scene. Will fetch the data for this board and re-render the scene.
     */
    public void refresh() {
        // Populate board

        this.boardText.setText("I am rendering board '" + this.boardID + "'");
    }
}
