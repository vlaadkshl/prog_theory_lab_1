package ua.nure.progtheory.lab.converters;

import org.springframework.stereotype.Component;
import ua.nure.progtheory.lab.business.Group;
import ua.nure.progtheory.lab.data.GroupData;

@Component
public class GroupConverter {

    public Group fromData(GroupData data) {
        return Group.builder()
                .id(data.getId())
                .name(data.getName())
                .build();
    }

    public GroupData toData(Group group) {
        return GroupData.builder()
                .id(group.getId())
                .name(group.getName())
                .build();
    }
}
