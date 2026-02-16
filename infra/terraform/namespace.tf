# ===============================================
# NAMESPACE
# ===============================================

resource "kubernetes_namespace" "stock" {
  metadata {
    name = "${var.service}-${var.environment}"

    labels = {
      name        = var.service
      environment = var.environment
      managed_by  = "terraform"
    }
  }
}
