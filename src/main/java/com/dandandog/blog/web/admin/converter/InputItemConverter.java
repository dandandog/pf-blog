package com.dandandog.blog.web.admin.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.dandandog.blog.web.admin.faces.vo.InputItemVo;
import org.primefaces.component.chips.Chips;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/27 14:27
 */
@Component("inputItemConverter")
public class InputItemConverter implements Converter<InputItemVo> {

    @Override
    public InputItemVo getAsObject(FacesContext context, UIComponent component, String value) {
        if (component instanceof Chips) {
            List<InputItemVo> items = getItemValue(context, component);
            if (CollUtil.isEmpty(items)) {
                return new InputItemVo(0, value);
            }
            return items.stream().filter(item -> item.getLabel().equals(value)).findFirst().orElse(new InputItemVo(items.size() - 1, value));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, InputItemVo value) {
        if (ObjectUtil.isNotNull(value)) {
            return value.getLabel();
        }
        return null;
    }

    private List<InputItemVo> getItemValue(FacesContext context, UIComponent component) {
        return (List<InputItemVo>) component.getValueExpression("value").getValue(context.getELContext());
    }

}
