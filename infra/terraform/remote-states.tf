# ===============================================
# REMOTE STATES
# ===============================================

# EKS Cluster
data "terraform_remote_state" "kubernetes" {
  backend = "s3"
  config = {
    bucket = "tf-state-challenge-bucket"
    key    = "kubernetes/${var.environment}/terraform.tfstate"
    region = "us-east-2"
  }
}

# RDS Stock Database
data "terraform_remote_state" "rds_stock" {
  backend = "s3"
  config = {
    bucket = "tf-state-challenge-bucket"
    key    = "rds-stock/${var.environment}/terraform.tfstate"
    region = "us-east-2"
  }
}
