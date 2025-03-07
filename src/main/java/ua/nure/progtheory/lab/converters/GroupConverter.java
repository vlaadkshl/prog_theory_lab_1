package ua.nure.progtheory.lab.converters;

import org.springframework.stereotype.Component;
import ua.nure.progtheory.lab.business.Group;
import ua.nure.progtheory.lab.data.GroupData;

@Component
public class GroupConverter implements Converter<Group, GroupData> {

    @Override
    public Group fromData(GroupData data) {
        if (data == null) {
            return null;
        }

        return Group.builder()
                .id(data.getId())
                .name(data.getName())
                .build();
    }

    @Override
    public GroupData toData(Group group) {
        if (group == null) {
            return null;
        }

        return GroupData.builder()
                .id(group.getId())
                .name(group.getName())
                .build();
    }
}
