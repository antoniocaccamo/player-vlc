/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.antanysavage.sm.player.sequences.model;

/**
 *
 * @author antonio.caccamo
 */
class Node<Media> {
    public Media element;
    public Node next;    

    @Override
    public String toString() {
        
        return element.toString();
    }

}
