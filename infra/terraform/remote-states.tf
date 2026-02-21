# ===============================================
# REMOTE STATES
# ===============================================

# EKS Cluster
data "terraform_remote_state" "kubernetes" {
  backend = "s3"
  config = {
    bucket = "tf-state-challenge-bucket"
    key    = local.kubernetes_state_path
    region = "us-east-2"
  }
}

# RDS Stock Database
data "terraform_remote_state" "rds_stock" {
  backend = "s3"
  config = {
    bucket = "tf-state-challenge-bucket"
    key    = local.rds_stock_state_path
    region = "us-east-2"
  }
}
