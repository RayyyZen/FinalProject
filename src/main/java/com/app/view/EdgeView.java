package com.app.view;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.Line;
import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.*;
import javafx.scene.input.*;
import javafx.animation.*;
import javafx.util.*;

public class EdgeView extends Line{

    public EdgeView(NodeView source, NodeView target) {

        //this.startXProperty().bind(source.layoutXProperty().add(source.getRadius()));
        //this.startYProperty().bind(source.layoutYProperty().add(source.getRadius()));

        // On lie la fin de la ligne à la position du nœud cible
        //this.endXProperty().bind(target.layoutXProperty().add(target.getRadius()));
        //this.endYProperty().bind(target.layoutYProperty().add(target.getRadius()));


        //this.setStrokeWidth(2.0);
    }

}
