package com.moscow.cineverse.base

import com.moscow.cinverse.presentation.R
import com.moscow.domain.exception.CineVerseException

fun Exception.handleException() : Int{
    return when(this){
        is CineVerseException -> when(this){
            CineVerseException.BadRequestException -> R.string.bad_request
            CineVerseException.AddMediaItemToCollectionException -> R.string.faild_to_add_item_please_try_again_later
            CineVerseException.ClearCollectionException -> R.string.faild_to_clear_collection_please_try_again_later
            CineVerseException.DeleteMediaItemFromCollectionException -> R.string.faild_to_delete_item_from_collection_please_try_again_later
            CineVerseException.ForbiddenRequestException -> R.string.forbidden_request
            CineVerseException.NoInternetException -> R.string.no_internet_connection
            CineVerseException.NotAllowedUserException -> R.string.not_allowed_user
            CineVerseException.NotFoundException -> R.string.not_found
            CineVerseException.NullException -> R.string.not_found
            CineVerseException.ServerErrorException -> R.string.server_error
            CineVerseException.ServerNotFoundException -> R.string.server_not_found
            CineVerseException.ServiceUnavailableException -> R.string.service_unavailable
            CineVerseException.TooManyRequestsException -> R.string.too_many_requests
            CineVerseException.TooMuchTimeException -> R.string.too_much_time
            CineVerseException.UnauthorizedRequestException -> R.string.unauthorized_request
            CineVerseException.UnknownException -> R.string.error_occurred
        }
        else -> R.string.error_occurred
    }
}