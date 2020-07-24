package is.web.mapper;


import is.domain.Card;
import is.web.model.CardDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface CardMapper {
    CardDto cardToCardDto(Card card);
    Card cardDtoToCard(CardDto cardDto);
}
