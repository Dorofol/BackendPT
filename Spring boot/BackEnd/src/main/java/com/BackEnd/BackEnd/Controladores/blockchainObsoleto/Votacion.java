package com.BackEnd.BackEnd.Controladores.blockchainObsoleto;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.5.0.
 */
@SuppressWarnings("rawtypes")
public class Votacion extends Contract {
    public static final String BINARY = "60806040525f600255348015610013575f80fd5b50610bae806100215f395ff3fe608060405234801561000f575f80fd5b5060043610610055575f3560e01c80632160683114610059578063503c560f146100755780636ff205fd146100915780638f023f85146100af578063a92284d0146100e1575b5f80fd5b610073600480360381019061006e9190610427565b610111565b005b61008f600480360381019061008a919061058e565b6102a1565b005b610099610315565b6040516100a691906105e4565b60405180910390f35b6100c960048036038101906100c49190610427565b61031b565b6040516100d893929190610677565b60405180910390f35b6100fb60048036038101906100f6919061070d565b6103c6565b6040516101089190610752565b60405180910390f35b60015f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff161561019b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610192906107b5565b60405180910390fd5b5f811180156101ac57506002548111155b6101eb576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101e29061081d565b60405180910390fd5b6001805f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff0219169083151502179055505f808281526020019081526020015f206002015f81548092919061026290610868565b91905055507f578bfe394f52688c8b1b6cde2980c89b24f12e200eb309e41aa13a4068fa8c4c8160405161029691906105e4565b60405180910390a150565b60025f8154809291906102b390610868565b9190505550604051806060016040528060025481526020018281526020015f8152505f8060025481526020019081526020015f205f820151815f015560208201518160010190816103049190610aa9565b506040820151816002015590505050565b60025481565b5f602052805f5260405f205f91509050805f01549080600101805461033f906108dc565b80601f016020809104026020016040519081016040528092919081815260200182805461036b906108dc565b80156103b65780601f1061038d576101008083540402835291602001916103b6565b820191905f5260205f20905b81548152906001019060200180831161039957829003601f168201915b5050505050908060020154905083565b6001602052805f5260405f205f915054906101000a900460ff1681565b5f604051905090565b5f80fd5b5f80fd5b5f819050919050565b610406816103f4565b8114610410575f80fd5b50565b5f81359050610421816103fd565b92915050565b5f6020828403121561043c5761043b6103ec565b5b5f61044984828501610413565b91505092915050565b5f80fd5b5f80fd5b5f601f19601f8301169050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b6104a08261045a565b810181811067ffffffffffffffff821117156104bf576104be61046a565b5b80604052505050565b5f6104d16103e3565b90506104dd8282610497565b919050565b5f67ffffffffffffffff8211156104fc576104fb61046a565b5b6105058261045a565b9050602081019050919050565b828183375f83830152505050565b5f61053261052d846104e2565b6104c8565b90508281526020810184848401111561054e5761054d610456565b5b610559848285610512565b509392505050565b5f82601f83011261057557610574610452565b5b8135610585848260208601610520565b91505092915050565b5f602082840312156105a3576105a26103ec565b5b5f82013567ffffffffffffffff8111156105c0576105bf6103f0565b5b6105cc84828501610561565b91505092915050565b6105de816103f4565b82525050565b5f6020820190506105f75f8301846105d5565b92915050565b5f81519050919050565b5f82825260208201905092915050565b5f5b83811015610634578082015181840152602081019050610619565b5f8484015250505050565b5f610649826105fd565b6106538185610607565b9350610663818560208601610617565b61066c8161045a565b840191505092915050565b5f60608201905061068a5f8301866105d5565b818103602083015261069c818561063f565b90506106ab60408301846105d5565b949350505050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6106dc826106b3565b9050919050565b6106ec816106d2565b81146106f6575f80fd5b50565b5f81359050610707816106e3565b92915050565b5f60208284031215610722576107216103ec565b5b5f61072f848285016106f9565b91505092915050565b5f8115159050919050565b61074c81610738565b82525050565b5f6020820190506107655f830184610743565b92915050565b7f59612068617320766f7461646f2e0000000000000000000000000000000000005f82015250565b5f61079f600e83610607565b91506107aa8261076b565b602082019050919050565b5f6020820190508181035f8301526107cc81610793565b9050919050565b7f43616e64696461746f206e6f2076616c69646f2e0000000000000000000000005f82015250565b5f610807601483610607565b9150610812826107d3565b602082019050919050565b5f6020820190508181035f830152610834816107fb565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f610872826103f4565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036108a4576108a361083b565b5b600182019050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f60028204905060018216806108f357607f821691505b602082108103610906576109056108af565b5b50919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f600883026109687fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8261092d565b610972868361092d565b95508019841693508086168417925050509392505050565b5f819050919050565b5f6109ad6109a86109a3846103f4565b61098a565b6103f4565b9050919050565b5f819050919050565b6109c683610993565b6109da6109d2826109b4565b848454610939565b825550505050565b5f90565b6109ee6109e2565b6109f98184846109bd565b505050565b5b81811015610a1c57610a115f826109e6565b6001810190506109ff565b5050565b601f821115610a6157610a328161090c565b610a3b8461091e565b81016020851015610a4a578190505b610a5e610a568561091e565b8301826109fe565b50505b505050565b5f82821c905092915050565b5f610a815f1984600802610a66565b1980831691505092915050565b5f610a998383610a72565b9150826002028217905092915050565b610ab2826105fd565b67ffffffffffffffff811115610acb57610aca61046a565b5b610ad582546108dc565b610ae0828285610a20565b5f60209050601f831160018114610b11575f8415610aff578287015190505b610b098582610a8e565b865550610b70565b601f198416610b1f8661090c565b5f5b82811015610b4657848901518255600182019150602085019450602081019050610b21565b86831015610b635784890151610b5f601f891682610a72565b8355505b6001600288020188555050505b50505050505056fea26469706673582212206d423041ae922307386ec89eedca1b53989e10a6a6486205063ac3ccec98360064736f6c63430008150033";

    public static final String FUNC_AGREGARCANDIDATO = "agregarCandidato";

    public static final String FUNC_CANDIDATOS = "candidatos";

    public static final String FUNC_CANDIDATOSCONTEO = "candidatosConteo";

    public static final String FUNC_VOTANTES = "votantes";

    public static final String FUNC_VOTAR = "votar";

    public static final Event VOTOEVENTO_EVENT = new Event("votoEvento", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Votacion(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Votacion(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Votacion(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Votacion(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<VotoEventoEventResponse> getVotoEventoEvents(TransactionReceipt transactionReceipt,Log log) {
        List<Contract.EventValuesWithLog> valueList = (List<EventValuesWithLog>) staticExtractEventParametersWithLog(VOTOEVENTO_EVENT, log);
        ArrayList<VotoEventoEventResponse> responses = new ArrayList<VotoEventoEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VotoEventoEventResponse typedResponse = new VotoEventoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.candidatoId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static VotoEventoEventResponse getVotoEventoEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTOEVENTO_EVENT, log);
        VotoEventoEventResponse typedResponse = new VotoEventoEventResponse();
        typedResponse.log = log;
        typedResponse.candidatoId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<VotoEventoEventResponse> votoEventoEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getVotoEventoEventFromLog(log));
    }

    public Flowable<VotoEventoEventResponse> votoEventoEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTOEVENTO_EVENT));
        return votoEventoEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> agregarCandidato(String _nombre) {
        final Function function = new Function(
                FUNC_AGREGARCANDIDATO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_nombre)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, String, BigInteger>> candidatos(BigInteger param0) {
        final Function function = new Function(FUNC_CANDIDATOS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, String, BigInteger>>(function,
                new Callable<Tuple3<BigInteger, String, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> candidatosConteo() {
        final Function function = new Function(FUNC_CANDIDATOSCONTEO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> votantes(String param0) {
        final Function function = new Function(FUNC_VOTANTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> votar(BigInteger _candidatoId) {
        final Function function = new Function(
                FUNC_VOTAR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_candidatoId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Votacion load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Votacion(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Votacion load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Votacion(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Votacion load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Votacion(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Votacion load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Votacion(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Votacion> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Votacion.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Votacion> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Votacion.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Votacion> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Votacion.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Votacion> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Votacion.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class VotoEventoEventResponse extends BaseEventResponse {
        public BigInteger candidatoId;
    }
}
