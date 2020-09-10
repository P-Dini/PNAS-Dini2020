package edu.louisville.cgemm.pouyasproject;

public class Gene
{
    String chrName;
    int startPos;
    int endPos;
    String geneSymbol;
    String geneId;
    
    public Gene() {
        this.chrName = null;
        this.startPos = -1;
        this.endPos = -1;
        this.geneSymbol = null;
        this.geneId = null;
    }
    
    public String getChrName() {
        return this.chrName;
    }
    
    public void setChrName(final String chrName) {
        this.chrName = chrName;
    }
    
    public int getStartPos() {
        return this.startPos;
    }
    
    public void setStartPos(final int startPos) {
        this.startPos = startPos;
    }
    
    public int getEndPos() {
        return this.endPos;
    }
    
    public void setEndPos(final int endPos) {
        this.endPos = endPos;
    }
    
    public String getGeneSymbol() {
        return this.geneSymbol;
    }
    
    public void setGeneSymbol(final String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }
    
    public String getGeneId() {
        return this.geneId;
    }
    
    public void setGeneId(final String geneId) {
        this.geneId = geneId;
    }
}