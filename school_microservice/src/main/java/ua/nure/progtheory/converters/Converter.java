package ua.nure.progtheory.converters;

public interface Converter<BOM, DTO> {

    BOM fromData(DTO dto);

    DTO toData(BOM bom);
}
