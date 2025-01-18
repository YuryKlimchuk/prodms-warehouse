package com.hydroyura.prodms.warehouse.server.validation.validators;

import static com.hydroyura.prodms.warehouse.server.validation.ValidationMessageCodes.DEFAULT_MSG;
import static com.hydroyura.prodms.warehouse.server.validation.ValidationMessageCodes.GET_ALL_MATERIALS_ITEMS_PER_PAGE_MIN;
import static com.hydroyura.prodms.warehouse.server.validation.ValidationMessageCodes.GET_ALL_MATERIALS_PAGE_NUM_MIN;
import static com.hydroyura.prodms.warehouse.server.validation.ValidationMessageCodes.GET_ALL_MATERIALS_SORT_CODE_MAX;
import static com.hydroyura.prodms.warehouse.server.validation.ValidationMessageCodes.GET_ALL_MATERIALS_SORT_CODE_MIN;

import com.hydroyura.prodms.warehouse.server.model.request.material.GetAllMaterialsReqParams;
import com.hydroyura.prodms.warehouse.server.props.ValidationGetAllMaterialParamsProps;
import com.hydroyura.prodms.warehouse.server.validation.AbstractValidator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class GetAllMaterialsReqParamsValidator extends AbstractValidator<GetAllMaterialsReqParams> {

    private static final String ITEMS_PER_PAGE = "itemsPerPage";
    private static final String PAGE_NUM = "pageNum";
    private static final String SORT_CODE = "sortCode";

    private final ValidationGetAllMaterialParamsProps props;


    public GetAllMaterialsReqParamsValidator(@Value("${validation.enabled}") Boolean enabled,
                                             ValidationGetAllMaterialParamsProps props) {
        super(GetAllMaterialsReqParams.class, enabled);
        this.props = props;
    }


    @Override
    protected void validateInternal(GetAllMaterialsReqParams target, Errors errors) {
        populateWithDefaults(target);
        validateItemsPerPage(target.getItemsPerPage(), errors);
        validatePageNum(target.getPageNum(), errors);
        validateSortCode(target.getSortCode(), errors);
    }

    private void validateItemsPerPage(Integer itemsPerPage, Errors errors) {
        if (itemsPerPage < props.getRules().getMinItemsPerPage()) {
            errors.rejectValue(ITEMS_PER_PAGE, GET_ALL_MATERIALS_ITEMS_PER_PAGE_MIN, DEFAULT_MSG);
        }
    }

    private void validatePageNum(Integer pageNum, Errors errors) {
        if (pageNum < props.getRules().getMinPageNum()) {
            errors.rejectValue(PAGE_NUM, GET_ALL_MATERIALS_PAGE_NUM_MIN, DEFAULT_MSG);
        }
    }

    private void validateSortCode(Integer sortCode, Errors errors) {
        if (sortCode < props.getRules().getMinSortCode()) {
            errors.rejectValue(SORT_CODE, GET_ALL_MATERIALS_SORT_CODE_MIN, DEFAULT_MSG);
        } else if (sortCode > props.getRules().getMaxSortCode()) {
            errors.rejectValue(SORT_CODE, GET_ALL_MATERIALS_SORT_CODE_MAX, DEFAULT_MSG);
        }
    }

    private void populateWithDefaults(final GetAllMaterialsReqParams params) {
        setDefault(params.getItemsPerPage(), params::setItemsPerPage, props.getDefaults()::getItemsPerPage);
        setDefault(params.getPageNum(), params::setPageNum, props.getDefaults()::getPageNum);
        setDefault(params.getSortCode(), params::setSortCode, props.getDefaults()::getSortCode);
    }

    private static <T> void setDefault(T field, Consumer<T> c, Supplier<T> s) {
        if (Objects.isNull(field)) {
            c.accept(s.get());
        }
    }
}
