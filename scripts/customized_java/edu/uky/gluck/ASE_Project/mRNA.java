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
public class mRNA {

    private String accession = null;
    private long   startPos = -1l;
    private long   endPos   = -1l;
    private String orientation = null;
    private ArrayList cds = new ArrayList();
    private ArrayList exon = new ArrayList();

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public ArrayList getCds() {
        return cds;
    }

    public void addCds(Feature featureIn) {
        cds.add(featureIn);
    }

    public void setCds(ArrayList cdsIn){
        cds = cdsIn;
    }

    public long getEndPos() {
        return endPos;
    }

    public void setEndPos(long endPos) {
        this.endPos = endPos;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }


    public ArrayList getExons() {
        return exon;
    }

    public void addExon(Feature featureIn) {
        exon.add(featureIn);
    }

    public void setExon(ArrayList exonIn){
        exon = exonIn;
    }

    public long getStartPos() {
        return startPos;
    }

    public void setStartPos(long startPos) {
        this.startPos = startPos;
    }

}
