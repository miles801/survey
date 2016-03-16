package eccrm.base.datadic.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Michael
 */
public class DataDicConditionParser {
    private DataDicCondition dataDicCondition;
    private Session session;

    public DataDicConditionParser(Session session, DataDicCondition dataDicCondition) {
        this.session = session;
        this.dataDicCondition = dataDicCondition;
    }

    public DetachedCriteria parse() {
        String className = dataDicCondition.getClassName();
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("数据字典配置错误，类[" + className + "]不存在!");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        List<DataDicItemCondition> conditions = dataDicCondition.getConditions();
        List<Criterion> criterionList = new ArrayList<Criterion>();
        if (conditions != null && !conditions.isEmpty()) {
            for (DataDicItemCondition item : conditions) {
                String operate = item.getOperate();
                String value = item.getValue();
                String valueType = item.getValueType();
                String fieldName = item.getFieldName();
                String conditionType = item.getConditionType();
                Criterion criterion = null;
                Object realValue = getRealValue(value, valueType);
                if (realValue == null && !conditionType.contains("null")) {
                    throw new RuntimeException("值不能为空!");
                }
                if ("eq".equals(conditionType)) {
                    criterion = Restrictions.eq(fieldName, realValue);
                } else if ("ne".equals(conditionType)) {
                    criterion = Restrictions.ne(fieldName, realValue);
                } else if ("gt".equals(conditionType)) {
                    criterion = Restrictions.gt(fieldName, realValue);
                } else if ("like".equals(conditionType)) {
                    criterion = Restrictions.like(fieldName, "%" + value + "%");
                } else if ("lt".equals(conditionType)) {
                    criterion = Restrictions.lt(fieldName, realValue);
                } else if ("ge".equals(conditionType)) {
                    criterion = Restrictions.ge(fieldName, realValue);
                } else if ("le".equals(conditionType)) {
                    criterion = Restrictions.le(fieldName, realValue);
                } else if ("in".equals(conditionType)) {
                    criterion = Restrictions.in(fieldName, ((String) realValue).split(","));
                } else if ("null".equals(conditionType)) {
                    criterion = Restrictions.isNull(fieldName);
                } else if ("notnull".equals(conditionType)) {
                    criterion = Restrictions.isNotNull(fieldName);
                } else {
                    throw new RuntimeException("数据字典配置错误!不能识别的条件类型:" + conditionType);
                }

                if ("AND".equals(operate)) {
                    criterionList.add(criterion);
                } else if ("OR".equals(operate)) {
                    // 如果是OR，则将之前的所有的条件都作为一个条件与当前条件进行OR运算
                    Disjunction disjunction = Restrictions.disjunction();
                    for (Criterion foo : criterionList) {
                        disjunction.add(foo);
                    }
                    criterionList.clear();
                    criteria.add(Restrictions.or(disjunction, criterion));
                } else {
                    throw new RuntimeException("数据字典配置错误!不能识别的操作类型:" + operate);
                }

            }
            // 如果没有OR或者有剩余，则添加到添加中
            if (!criterionList.isEmpty()) {
                for (Criterion foo : criterionList) {
                    criteria.add(foo);
                }
            }
        }
        return criteria;
    }

    /**
     * 获得真正类型的值
     *
     * @param value     字符串值
     * @param valueType 值类型
     */
    private Object getRealValue(String value, String valueType) {
        Object realValue = null;
        if ("string".equals(valueType)) {
            realValue = value;
        } else if ("int".equals(valueType)) {
            if (!value.matches("\\d+")) {
                throw new RuntimeException("错误的值!值只能是整数!");
            }
            realValue = Integer.parseInt(value);
        } else if ("double".equals(valueType)) {
            if (!value.matches("\\d+(\\.\\d+)?")) {
                throw new RuntimeException("错误的值!值只能是小数!");
            }
            realValue = Double.parseDouble(value);
        } else if ("date".equals(valueType)) {
            if (value.matches("\\d+")) {
                realValue = new Date(Long.parseLong(value));
            } else if (value.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}(\\.\\d{3})?")) {
                try {
                    realValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                try {
                    realValue = new SimpleDateFormat("yyyy-MM-dd").parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                throw new RuntimeException("错误的值!时间类型的值只能是Long型毫秒值!");
            }
        } else if ("boolean".equals(valueType)) {
            if ("true".equals(value)) {
                realValue = true;
            } else if ("false".equals(value)) {
                realValue = false;
            } else {
                throw new RuntimeException("错误的值!值只能是true或者false!");
            }
        }
        return realValue;
    }
}
