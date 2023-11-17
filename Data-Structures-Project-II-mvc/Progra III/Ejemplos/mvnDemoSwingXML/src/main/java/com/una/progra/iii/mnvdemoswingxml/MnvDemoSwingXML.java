/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.una.progra.iii.mnvdemoswingxml;

import Controller.CtrlDemoMVCSwingXML;
import javax.xml.transform.TransformerException;
//import com.una.progra.iii.view.VLogin;

/**
 *
 * @author josev
 */
public class MnvDemoSwingXML {

    public static void main(String[] args) throws TransformerException {
        System.out.println("-----------------------------------------------");
        System.out.println("Starting execution from MnvDemoSwingXML.Main...");
        
        //CtrlDemoMVCSwingXML mainCtrl = new CtrlDemoMVCSwingXML(initialForm);
        CtrlDemoMVCSwingXML mainCtrl = new CtrlDemoMVCSwingXML();
        
        mainCtrl.InitGUI();
        
        System.out.println("end of MnvDemoSwingXML.main... but not execution");
        System.out.println("");
        System.out.println("The control is set to initialize the MVC pattern -> Controller");
        
    }
}
