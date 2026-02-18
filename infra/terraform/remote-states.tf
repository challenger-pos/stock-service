# ===============================================
# REMOTE STATES
# ===============================================

# EKS Cluster
data "terraform_remote_state" "kubernetes" {
  backend = "s3"
  config = {
    bucket = "tf-state-challenge-bucket"
    key    = "v4/kubernetes/dev/terraform.tfstate"
    region = "us-east-2"
  }
}

# RDS Stock Database
data "terraform_remote_state" "rds_stock" {
  backend = "s3"
  config = {
    bucket = "tf-state-challenge-bucket"
    key    = "v4/rds-stock/dev/terraform.tfstate"
    region = "us-east-2"
  }
}
