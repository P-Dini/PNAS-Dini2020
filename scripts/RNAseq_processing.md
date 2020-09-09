# Processing of raw RNA-seq reads.

## Trimming using TrimGalore v0.4.3
```
trim_galore --fastqc --paired --quality 30 --output_dir <> --adapter AGATCGGAAGAGCACACGTCTGAACTCCAGTCACNNNNNNATCTCGTATGCCGTCTTCTGCTTG  --adapter2 AGATCGGAAGAGCGTCGTGTAGGGAAAGAGTGTAGATCTCGGTGGTCGCCGTATCAT 1.fastq 2.fastq
```
