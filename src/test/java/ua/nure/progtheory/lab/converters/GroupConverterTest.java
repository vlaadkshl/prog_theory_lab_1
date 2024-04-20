package ua.nure.progtheory.lab.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import ua.nure.progtheory.lab.business.Group;
import ua.nure.progtheory.lab.data.GroupData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GroupConverterTest {

    @InjectMocks
    private GroupConverter groupConverter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void fromDataShouldReturnGroupWhenDataIsNotNull() {
        long id = 1L;
        String testGroup = "Test Group";

        GroupData data = GroupData.builder().id(id).name(testGroup).build();

        Group result = groupConverter.fromData(data);

        assertEquals(id, result.getId());
        assertEquals(testGroup, result.getName());
    }

    @Test
    public void fromDataShouldReturnNullWhenDataIsNull() {
        Group result = groupConverter.fromData(null);

        assertNull(result);
    }

    @Test
    public void toDataShouldReturnGroupDataWhenGroupIsNotNull() {
        long id = 1L;
        String testGroup = "Test Group";

        Group group = Group.builder().id(id).name(testGroup).build();

        GroupData result = groupConverter.toData(group);

        assertEquals(id, result.getId());
        assertEquals(testGroup, result.getName());
    }

    @Test
    public void toDataShouldReturnNullWhenGroupIsNull() {
        GroupData result = groupConverter.toData(null);

        assertNull(result);
    }
}