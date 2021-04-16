module com.silviolimeira.desafio.ui {
    requires javafx.graphics;
    requires javafx.controls;
    requires com.silviolimeira.desafio.tools;
    exports com.silviolimeira.desafio.ui;
    opens com.silviolimeira.desafio.model to javafx.base;
}