package MyLayout;

import javafx.scene.control.Dialog;

public class HandleSocketMessages {
    Dialog dialog;
    JavaSocket javaSocket;

    public HandleSocketMessages(Dialog dialog, JavaSocket javaSocket) {
        this.dialog = dialog;
        this.javaSocket = javaSocket;
    }

    public void setMessage(String message){

    }


    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public JavaSocket getJavaSocket() {
        return javaSocket;
    }

    public void setJavaSocket(JavaSocket javaSocket) {
        this.javaSocket = javaSocket;
    }
}
