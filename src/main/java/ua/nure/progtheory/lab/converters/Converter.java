package ua.nure.progtheory.lab.converters;

public interface Converter<BOM, DTO> {

    BOM fromData(DTO dto);

    DTO toData(BOM bom);
}
