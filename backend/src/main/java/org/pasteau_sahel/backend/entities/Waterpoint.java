package org.pasteau_sahel.backend.entities;

import org.locationtech.jts.geom.Point;
import javax.persistence.*;

@Entity
@Table(name = "a_oeuvres")
public class Waterpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "a_oeuvres_gid_seq")
    @SequenceGenerator(name = "a_oeuvres_gid_seq", sequenceName = "a_oeuvres_gid_seq", allocationSize = 1)
    private Integer gid;

    @Column(name = "no_book", length = 254)
    private String noBook;

    @Column(name = "old_no_", length = 254)
    private String oldNo;

    @Column(name = "ancient_irh", length = 254)
    private String ancientIrh;

    @Column(name = "code_villa", length = 254)
    private String codeVilla;

    @Column(length = 254)
    private String neighborhood;

    @Column(name = "project_code")
    private Long projectCode;

    @Column(name = "code_between")
    private Long codeBetween;

    @Column(name = "code_aep", length = 254)
    private String codeAep;

    @Column(name = "date_debut", length = 254)
    private String dateDebut;

    @Column(name = "date_real", length = 254)
    private String dateReal;

    @Column(name = "cat_ouvrag", length = 254)
    private String catOuvrag;

    @Column(name = "type_open", length = 254)
    private String typeOpen;

    private Long longitude;
    private Long latitude;

    private Long altitude;

    @Column(name = "code_usage", length = 254)
    private String codeUsage;

    @Column(name = "nature_alt", length = 254)
    private String natureAlt;

    @Column(length = 254)
    private String geomorphol;

    @Column(name = "class_open")
    private Long classOpen;

    @Column(name = "prof_fract")
    private Double profFract;

    @Column(name = "prof_roof")
    private Double profRoof;

    @Column(name = "prof_mur")
    private Double profMur;

    @Column(name = "aquif_name", length = 254)
    private String aquifName;

    @Column(name = "code_aquif", length = 254)
    private String codeAquif;

    @Column(name = "litho_aqui", length = 254)
    private String lithoAqui;

    @Column(name = "code_litho", length = 254)
    private String codeLitho;

    @Column(name = "type_aquif", length = 254)
    private String typeAquif;

    @Column(name = "type_tablecloth", length = 254)
    private String typeTablecloth;

    @Column(name = "tech_for", length = 254)
    private String techFor;

    @Column(name = "prof_alter", length = 254)
    private String profAlter;

    @Column(name = "thick_alt")
    private Long thickAlt;

    @Column(length = 254)
    private String observatio;

    @Column(name = "date_saisi", length = 254)
    private String dateSaisi;

    @Column(length = 254)
    private String reliability;

    @Column(name = "analysis_ch", length = 254)
    private String analysisCh;

    @Column(name = "file_log_s", length = 254)
    private String fileLogS;

    @Column(name = "essay_file", length = 254)
    private String essayFile;

    @Column(name = "date_recep", length = 254)
    private String dateRecep;

    @Column(name = "code_vil_1", length = 254)
    private String codeVil1;

    private Long revisit;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point geom;

    @Column(length = 10)
    private String zone;

    //<editor-fold desc="Getters and Setters">
    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getNoBook() {
        return noBook;
    }

    public void setNoBook(String noBook) {
        this.noBook = noBook;
    }

    public String getOldNo() {
        return oldNo;
    }

    public void setOldNo(String oldNo) {
        this.oldNo = oldNo;
    }

    public String getAncientIrh() {
        return ancientIrh;
    }

    public void setAncientIrh(String ancientIrh) {
        this.ancientIrh = ancientIrh;
    }

    public String getCodeVilla() {
        return codeVilla;
    }

    public void setCodeVilla(String codeVilla) {
        this.codeVilla = codeVilla;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Long getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(Long projectCode) {
        this.projectCode = projectCode;
    }

    public Long getCodeBetween() {
        return codeBetween;
    }

    public void setCodeBetween(Long codeBetween) {
        this.codeBetween = codeBetween;
    }

    public String getCodeAep() {
        return codeAep;
    }

    public void setCodeAep(String codeAep) {
        this.codeAep = codeAep;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateReal() {
        return dateReal;
    }

    public void setDateReal(String dateReal) {
        this.dateReal = dateReal;
    }

    public String getCatOuvrag() {
        return catOuvrag;
    }

    public void setCatOuvrag(String catOuvrag) {
        this.catOuvrag = catOuvrag;
    }

    public String getTypeOpen() {
        return typeOpen;
    }

    public void setTypeOpen(String typeOpen) {
        this.typeOpen = typeOpen;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getAltitude() {
        return altitude;
    }

    public void setAltitude(Long altitude) {
        this.altitude = altitude;
    }

    public String getCodeUsage() {
        return codeUsage;
    }

    public void setCodeUsage(String codeUsage) {
        this.codeUsage = codeUsage;
    }

    public String getNatureAlt() {
        return natureAlt;
    }

    public void setNatureAlt(String natureAlt) {
        this.natureAlt = natureAlt;
    }

    public String getGeomorphol() {
        return geomorphol;
    }

    public void setGeomorphol(String geomorphol) {
        this.geomorphol = geomorphol;
    }

    public Long getClassOpen() {
        return classOpen;
    }

    public void setClassOpen(Long classOpen) {
        this.classOpen = classOpen;
    }

    public Double getProfFract() {
        return profFract;
    }

    public void setProfFract(Double profFract) {
        this.profFract = profFract;
    }

    public Double getProfRoof() {
        return profRoof;
    }

    public void setProfRoof(Double profRoof) {
        this.profRoof = profRoof;
    }

    public Double getProfMur() {
        return profMur;
    }

    public void setProfMur(Double profMur) {
        this.profMur = profMur;
    }

    public String getAquifName() {
        return aquifName;
    }

    public void setAquifName(String aquifName) {
        this.aquifName = aquifName;
    }

    public String getCodeAquif() {
        return codeAquif;
    }

    public void setCodeAquif(String codeAquif) {
        this.codeAquif = codeAquif;
    }

    public String getLithoAqui() {
        return lithoAqui;
    }

    public void setLithoAqui(String lithoAqui) {
        this.lithoAqui = lithoAqui;
    }

    public String getCodeLitho() {
        return codeLitho;
    }

    public void setCodeLitho(String codeLitho) {
        this.codeLitho = codeLitho;
    }

    public String getTypeAquif() {
        return typeAquif;
    }

    public void setTypeAquif(String typeAquif) {
        this.typeAquif = typeAquif;
    }

    public String getTypeTablecloth() {
        return typeTablecloth;
    }

    public void setTypeTablecloth(String typeTablecloth) {
        this.typeTablecloth = typeTablecloth;
    }

    public String getTechFor() {
        return techFor;
    }

    public void setTechFor(String techFor) {
        this.techFor = techFor;
    }

    public String getProfAlter() {
        return profAlter;
    }

    public void setProfAlter(String profAlter) {
        this.profAlter = profAlter;
    }

    public Long getThickAlt() {
        return thickAlt;
    }

    public void setThickAlt(Long thickAlt) {
        this.thickAlt = thickAlt;
    }

    public String getObservatio() {
        return observatio;
    }

    public void setObservatio(String observatio) {
        this.observatio = observatio;
    }

    public String getDateSaisi() {
        return dateSaisi;
    }

    public void setDateSaisi(String dateSaisi) {
        this.dateSaisi = dateSaisi;
    }

    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }

    public String getAnalysisCh() {
        return analysisCh;
    }

    public void setAnalysisCh(String analysisCh) {
        this.analysisCh = analysisCh;
    }

    public String getFileLogS() {
        return fileLogS;
    }

    public void setFileLogS(String fileLogS) {
        this.fileLogS = fileLogS;
    }

    public String getEssayFile() {
        return essayFile;
    }

    public void setEssayFile(String essayFile) {
        this.essayFile = essayFile;
    }

    public String getDateRecep() {
        return dateRecep;
    }

    public void setDateRecep(String dateRecep) {
        this.dateRecep = dateRecep;
    }

    public String getCodeVil1() {
        return codeVil1;
    }

    public void setCodeVil1(String codeVil1) {
        this.codeVil1 = codeVil1;
    }

    public Long getRevisit() {
        return revisit;
    }

    public void setRevisit(Long revisit) {
        this.revisit = revisit;
    }

    public Point getGeom() {
        return geom;
    }

    public void setGeom(Point geom) {
        this.geom = geom;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
    //</editor-fold>
}