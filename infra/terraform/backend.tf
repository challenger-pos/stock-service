terraform {
  backend "s3" {
    bucket         = "tf-state-challenge-bucket"
    key            = "v4/service-stock/dev/terraform.tfstate"
    region         = "us-east-2"
  }
}
