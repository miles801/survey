package eccrm.base.employee.service.impl;

import com.michael.poi.core.Converter;

/**
 * @author Michael
 */
public class BooleanConverter implements Converter<EmployeeDTO> {
    @Override
    public Object execute(EmployeeDTO employeeDTO, Object o) {
        if (o != null && o.toString().equals("是")) {
            return true;
        }
        return false;
    }
}
