locals {
  rds_stock_state_path = "v4/rds-stock/${var.environment}/terraform.tfstate"
}

locals {
  kubernetes_state_path = "v4/kubernetes/${var.environment}/terraform.tfstate"
}