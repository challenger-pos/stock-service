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

    # JWT Secret
    JWT_SECRET = var.jwt_secret

    # AWS Credentials
    AWS_ACCESS_KEY_ID     = var.aws_access_key_id
    AWS_SECRET_ACCESS_KEY = var.aws_secret_access_key
  }
}
