package com.softwarecleandevelopment.crypto_chains.tether.data.datasource

import com.softwarecleandevelopment.core.common.utils.Constants
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Bip32ECKeyPair
import org.web3j.crypto.Credentials
import org.web3j.crypto.MnemonicUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.http.HttpService
import java.math.BigInteger
import javax.inject.Inject

private const val HARDEDED_BIT: Int = -0x80000000
private const val TETHER_CONTRACT_ADDRESS_MAINNET = "0xdAC17F958D2ee523a2206206994597C13D831ec7"

class TetherDataSourceImpl @Inject constructor() : TetherDataSource {
    override suspend fun generateAddress(
        params: AddressParams,
    ): String {
        val seed = MnemonicUtils.generateSeed(params.mnemonic, params.passPhrase)
        val masterKeyPair = Bip32ECKeyPair.generateKeyPair(seed)
        val path = intArrayOf(
            44 or HARDEDED_BIT,
            60 or HARDEDED_BIT,
            0 or HARDEDED_BIT,
            0,
            params.accountIndex,
        )
        val derivedKeyPair = Bip32ECKeyPair.deriveKeyPair(masterKeyPair, path)
        val credentials = Credentials.create(derivedKeyPair)
        val address = credentials.address
        return address
    }

    /**
     * Fetches the raw balance of an ERC-20 token for a given wallet address.
     * * @param web3j The connected Web3j client.
     * @param address The account to check the balance for.
     * @return The raw balance as a BigInteger (needs to be divided by token decimals).
     */
    override suspend fun getTetherBalance(address: String): String = withContext(Dispatchers.IO) {
        val web3: Web3j = Web3j.build(HttpService(Constants.rpcUrl))
        // Define the ERC-20 balanceOf function call
        val function = Function(
            "balanceOf",
            listOf(Address(address)), // Input parameter: the wallet address
            listOf<TypeReference<*>>(object : TypeReference<Uint256>() {}) // Output type: uint256
        )

        //Encode the function call data
        val encodedFunction = FunctionEncoder.encode(function)

       //Create the EthCall transaction object (read-only)
        val ethCall = Transaction.createEthCallTransaction(
            null, // No 'from' address needed for a read-only call
            TETHER_CONTRACT_ADDRESS_MAINNET,
            encodedFunction
        )

        //Send the call request synchronously (or use sendAsync().await() with coroutines)
        val response = web3.ethCall(ethCall, DefaultBlockParameterName.LATEST).send()

        //Decode the raw response
        val results = FunctionReturnDecoder.decode(response.value, function.outputParameters)

        if (results.isNotEmpty() && results[0] is Uint256) {
            val rawBalance: BigInteger = (results[0] as Uint256).value

            //Convert the raw balance (wei) using the correct number of decimals (6 for USDT)
            val divisor = BigInteger.TEN.pow(6)
            val balance = rawBalance.toBigDecimal().divide(divisor.toBigDecimal())

            return@withContext balance.toPlainString()
        } else {
            throw IllegalStateException("Failed to retrieve or decode token balance.")
        }
    }
}