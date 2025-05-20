package com.pavel_fomchenkov.subscriptions.mapper;

import com.pavel_fomchenkov.subscriptions.dto.SubscriptionDTO;
import com.pavel_fomchenkov.subscriptions.model.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubscriptionMapper {
    @Named("mapToSubscriptionDTO")
    SubscriptionDTO mapToSubscriptionDTO(Subscription subscription);

    @Named("mapDTOToSubscription")
    Subscription mapDTOToSubscription(SubscriptionDTO subscriptionDTO);


}
