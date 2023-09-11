package thiagoDRangel.dto;

import thiagoDRangel.models.Investments;
import java.math.BigDecimal;
import java.util.List;

public record InvestmentsDto(List<String> types, BigDecimal amount) {

    public InvestmentsDto(Investments model) {

        this(model.getTypes(), model.getAmount());
    }

    public static InvestmentsDto from(Investments model) {
        return new InvestmentsDto(model.getTypes(), model.getAmount());
    }

    public Investments toModel() {
        Investments model = new Investments();
        model.setTypes(this.types);
        model.setAmount(this.amount);
        return model;
    }


}