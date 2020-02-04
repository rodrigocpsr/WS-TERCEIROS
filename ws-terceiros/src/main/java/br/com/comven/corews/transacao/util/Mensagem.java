package br.com.comven.corews.transacao.util;

public class Mensagem {

	//mensagens globais de validação
	public static final String MODEL_SIZE_VALIDATION_MSG = "O tamanho informado para o campo {0} é maior do que o esperado: {1}";
	public static final String MODEL_NAN_VALIDATION_MSG = "{0} deve ser composto somente por números";
	public static final String MODEL_MANDATORY_VALIDATION_MSG = "{0} não informado(a)";
	public static final String MODEL_INVALID_VALIDATION_MSG = "{0} inválido(a)";
	public static final String MODEL_INVALID_VALIDATION_MSG_DETALHE = "{0} inválido(a), utilize o formato {1}";
	public static final String MODEL_INVALID_INTERVAL = "{0} inválido(a). O valor informado deve ser maior que {1} e menor que {2}";
	public static final String MODEL_NOT_ALLOWED_MSG = "{0} não permitido(a)";
	public static final String MODEL_NOT_FOUND_MSG = "{0} não encontrado(a)";
	public static final String MODEL_DUPLICATE = "{0} já cadastrado";
	public static final String MODEL_UPDATE_RESTRICTION_VALIDATION_MSG = "{0} não pode ser alterado";
	public static final String MODEL_API_GATEWAY = "Sistema de integração fora do ar. Aguarde e tente novamente mais tarde.";
	public static final String MODEL_PERIODO_INVALIDO = "A data inicial não pode ser posterior a data final";
	public static final String MODEL_PERIODO_HORA_AUSENTE = "A hora inicial e hora final devem ser informadas para compor o intervalo";
	public static final String MODEL_PERIODO_INTERVALO_INVALIDO = "{0} inválido(a). A diferença entre as horas deve ser no máximo {1}";
	
	
	//mensagens globais de sucesso e erro
	public static final String MODEL_SUCESSO = "Processamento executado com sucesso";
	public static final String MODEL_NAO_ENCONTRADO = "Nenhum registro encontrado";
	public static final String ERROR_BAD_REQUEST = "Requisição Inválida";
	
	//SEGURANÇA
	public static final String ERROR_TOKEN_INVALIDO = "Token de acesso inválido";
	public static final String ERROR_TOKEN_EXPIRADO = "Autenticação expirada";
	public static final String ERROR_TOKEN_MALFORMED = "Não foi possivel obter informações do token de acesso";
	public static final String ERROR_PERMISSAO = "Você não possui permissão para executar essa ação";
	public static final String AUTENTICACAO_USUARIO_NAO_ENCONTRADO = "Dados não conferem";
	public static final String AUTENTICACAO_USUARIO_CLIENTE_EXCLUIDO = "Cliente encontra-se inativo, entre em contato com o administrador";
	public static final String HORARIO_BLOQUEIO = "Não é permitido a execução de transações no horario, para mais informações entre em contato com o suporte.";
	public static final String CVE_BLOQUEADA = "A comunicação de venda foi bloqueada pelo sistema, para mais informações entre em contato com o suporte.";
	
	public static final String PERMISSAO_WS = "Cliente não possui permissão para executar este serviço. Entre em contato com o administrador";
	
	//USUARIO
	public static final String ERROR_EMAIL_JA_CADASTRADO = "E-mail informado já encontra-se cadastrado";
	public static final String ERROR_USUARIO_SENHA_TAMANHO = "A senha deve ter entre 8 e 16 caracteres";
	public static final String ERROR_USUARIO_SENHA_CASE = "A senha deve ter pelo menos um caractere Maiusculo e um Minusculo";
	public static final String ERROR_USUARIO_JA_CADASTRADO = "Já existe um usuário cadastrado com o e-mail informado";
	
	public static final String ERROR_OPERADOR_NAO_ENCONTRADO = "Operador não encontrado";
	
	//CLIENTE
	public static final String CLIENTE_PARCEIRO_SEM_RESPONSAVEL = "O parceiro deve informar o CNPJ do cliente responsavel pela transação.";
	public static final String CLIENTE_NAO_PERMITIDO = "operação não permitida para o CNPJ";
	public static final String CLIENTE_NAO_PERMITIDO_CONSULTA_PREPAGO = "Não foi possível realizar a consulta. Favor verificar os dados informados.";
	
	//TRANSACAO
	public static final String TRANSACAO_ANO_MODELO_ANO_FAB = "O Ano modelo não pode ser menor que o ano de fabricação.";
	
	
}
