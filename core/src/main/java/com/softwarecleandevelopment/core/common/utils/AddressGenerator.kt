package com.softwarecleandevelopment.core.common.utils

import com.softwarecleandevelopment.core.crypto.models.AddressParams

interface AddressGenerator {
   suspend fun invoke(params: AddressParams):  Resource<String>
}