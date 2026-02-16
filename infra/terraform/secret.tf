# ===============================================
# SECRET - CONFIGURAÇÕES SENSÍVEIS
# ===============================================

resource "kubernetes_secret" "app_secret" {
  metadata {
    name      = "${var.app_name}-secret"
    namespace = kubernetes_namespace.stock.metadata[0].name
  }

  type = "Opaque"

  data = {
    # Database Password
    SPRING_DATASOURCE_PASSWORD = var.db_password

    # AWS Credentials para SQS
    AWS_ACCESS_KEY_ID     = var.aws_access_key
    AWS_SECRET_ACCESS_KEY = var.aws_secret_key

    # AWS SDK Properties (alternativa para Spring Cloud AWS)
    "aws.access-key-id"     = var.aws_access_key
    "aws.secret-access-key" = var.aws_secret_key

    # JWT Secret
    JWT_SECRET = var.jwt_secret
  }
}
