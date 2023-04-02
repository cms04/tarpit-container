package de.cms04.importer.step.importDataStep;

import lombok.Data;

@Data
public class RawData {
    private String date;
    private String time;
    private String ip;
    private Integer port;
    private String type;
    private String isoCode;
    private String country;
}
