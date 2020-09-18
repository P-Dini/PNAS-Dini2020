/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uky.gluck.ASE_Project;

import java.util.ArrayList;

/**
 *
 * @author tedkalbfleisch
 */
public class Gene {

    long startPos = -1l;
    long endPos   = -1l;
    String geneId = null;
    String geneSymbol = null;
    ArrayList mrnaArray = new ArrayList();
    String orientation = null;
    String chromosome  = null;

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }



    public long getEndPos() {
        return endPos;
    }

    public void setEndPos(long endPos) {
        this.endPos = endPos;
    }

    public ArrayList getMrnaArray() {
        return mrnaArray;
    }

    public void addMrna(mRNA mrnaIn) {
        mrnaArray.add(mrnaIn);
    }

    public long getStartPos() {
        return startPos;
    }

    public void setStartPos(long startPos) {
        this.startPos = startPos;
    }

    public void setGeneId(String geneId) {
    	this.geneId = geneId;
    }
    
    public void setGeneSymbol(String geneSymbol) {
    	this.geneSymbol = geneSymbol;
    }
    
    public String getGeneId() {
    	return geneId;
    }
    
    public String getGeneSymbol() {
    	return geneSymbol;
    }

}
