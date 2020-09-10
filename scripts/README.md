++++++++++++++++++++++++++++++

Author: Pouya Dini

Publication: P. Dini et al. Parental bias in placental gene expression: A closer look at reciprocal paternal and maternal gene interaction in the equine placenta.


**Description:**

- WSG_processing.md and RNAseq_processing.md include the steps to be run in command line for quality check, trimming, mapping, and sorting of raw reads.

- Java scripts to identify SNPs from RNA-seq and find their parent of origin from WGS data (FindingParentalStatusofExpressedAlleles.java, FindingParentalStatusAllelesIngDNA, AnnonatingInformativeSNPsToTranscriptome.java).

- Java script for proper annotation of methylated C identified from reduced representation bisulfite sequencing (RRBS) to the equine transcriptome (AnnotatingMethylatedCtoTranscriptome.java).

- All intermediate scripts needed to organize, process, and define different variables can be found in the jar files. These can be downloaded and will include all necessary class and java files to properly run the complete pipeline.

++++++++++++++++++++++++++++++


Diagram with the overall workflow followed in the analyses:

![pipeline](https://github.com/jmuribes/images/blob/master/Dinietal2020_diagram.png)
