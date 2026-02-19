# ===============================================
# CONFIGMAP - CONFIGURAÇÕES NÃO-SENSÍVEIS
# ===============================================

resource "kubernetes_config_map" "app_config" {
  metadata {
    name      = "${var.app_name}-config"
    namespace = kubernetes_namespace.stock.metadata[0].name
  }

  data = {
    # Spring Boot
    SPRING_PROFILES_ACTIVE = var.spring_profiles_active
    SERVER_PORT           = tostring(var.app_port)
    SERVER_SERVLET_CONTEXT_PATH = "/api"

    # Database
    SPRING_DATASOURCE_URL      = "jdbc:postgresql://${data.terraform_remote_state.rds_stock.outputs.rds_endpoint_without_port}:${data.terraform_remote_state.rds_stock.outputs.rds_port}/${data.terraform_remote_state.rds_stock.outputs.db_name}"
    SPRING_DATASOURCE_USERNAME = var.db_username
    SPRING_DATASOURCE_DRIVER_CLASS_NAME = "org.postgresql.Driver"

    # JPA/Hibernate
    SPRING_JPA_HIBERNATE_DDL_AUTO = "none"
    SPRING_JPA_SHOW_SQL           = "false"
    SPRING_JPA_OPEN_IN_VIEW       = "false"
    SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL = "false"
    SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT    = "org.hibernate.dialect.PostgreSQLDialect"

    # Liquibase
    SPRING_LIQUIBASE_ENABLED     = "true"
    SPRING_LIQUIBASE_CHANGE_LOG  = "classpath:db/changelog/main-changelog.xml"

    # AWS
    AWS_REGION = var.aws_region

    # SQS Queue Names (consumed by stock-service)
    SQS_QUEUE_STOCK_REQUESTED        = aws_sqs_queue.stock_requested.name
    SQS_QUEUE_STOCK_APPROVED         = aws_sqs_queue.stock_approved.name
    SQS_QUEUE_STOCK_CANCEL_REQUESTED = aws_sqs_queue.stock_cancel_requested.name

    # SQS Queue Names (published by stock-service)
    SQS_QUEUE_STOCK_RESERVED = aws_sqs_queue.stock_reserved.name
    SQS_QUEUE_STOCK_FAILED   = aws_sqs_queue.stock_failed.name

    # Spring Cloud AWS SQS Properties
    "sqs.queue.stock-requested"       = aws_sqs_queue.stock_requested.name
    "sqs.queue.stock-approved"        = aws_sqs_queue.stock_approved.name
    "sqs.queue.stock-cancel-requested" = aws_sqs_queue.stock_cancel_requested.name
    "sqs.queue.stock-reserved"        = aws_sqs_queue.stock_reserved.name
    "sqs.queue.stock-failed"          = aws_sqs_queue.stock_failed.name

    # AWS SDK Properties
    "aws.access-key-id"     = ""  # Vazio, será sobrescrito pelo Secret
    "aws.secret-access-key" = ""  # Vazio, será sobrescrito pelo Secret

    # Actuator/Management
    MANAGEMENT_ENDPOINT_HEALTH_PROBES_ENABLED = "true"
    MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE = "health,info,liveness,readiness,prometheus"
    MANAGEMENT_ENDPOINT_HEALTH_SHOW_DETAILS   = "always"

    # Swagger/OpenAPI
    SPRINGDOC_API_DOCS_PATH = "/documentation"

    # Logging
    LOGGING_LEVEL_ROOT                      = "INFO"
    LOGGING_LEVEL_COM_FIAP                  = "INFO"
    LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_WEB   = "INFO"
    LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY = "WARN"
    LOGGING_LEVEL_ORG_HIBERNATE             = "WARN"
    LOGGING_PATTERN_CONSOLE                 = "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"

    # Datadog
    DD_TRACE_ENABLED                      = "true"
    DD_SERVICE                            = var.service
    DD_TRACE_HTTP_CLIENT_TAG_QUERY_STRING = "true"
    DD_TRACE_JDBC_ENABLED                 = "true"
    DD_TRACE_LOGS_INJECTION               = "true"

    # Datadog StatsD (DogStatsD for custom metrics)
    DATADOG_STATSD_HOST = var.datadog_agent_host
    DATADOG_STATSD_PORT = "8125"
  }
}
