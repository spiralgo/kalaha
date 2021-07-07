import {store} from "react-notifications-component";

export function showNotification(title, type, message){
    store.addNotification({
        title: title,
        message: message,
        type: type,
        insert: "bottom",
        container: "bottom-left",
        animationIn: ["animate__animated", "animate__fadeIn"],
        animationOut: ["animate__animated", "animate__fadeOut"],
        dismiss: {
            duration: 5000,
            onScreen: true
        }
    });
}