/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.resources;

import javafx.scene.image.Image;

/**
 *
 * @author adrinfer
 */
public class Images {
    
    
    public final static String CLOSE_ICON = "/es/chatserver/resources/closeIconTransparent.png";
    public final static String CLOSE_ICON_HOVER = "/es/chatserver/resources/closeIconHoverTransparent.png";
    public final static String MAX_ICON = "/es/chatserver/resources/maxIconTransparent.png";
    public final static String MAX_ICON_HOVER = "/es/chatserver/resources/maxIconHoverTransparent.png";
    public final static String RESIZE_ICON = "/es/chatserver/resources/resizeIcon.png";
    public final static String RESIZE_ICON_HOVER = "/es/chatserver/resources/resizeIconHover.png";
    public final static String RESIZE_ICON_PRESSED = "/es/chatserver/resources/resizeIconPressed.png";
    

    
    
    public static Image getImage(String url)
    {
        return new Image(url);
    }
    
    
}
