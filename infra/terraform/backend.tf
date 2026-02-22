terraform {
  backend "s3" {
    bucket         = "tf-state-challenge-bucket"
    region         = "us-east-2"
    # Será passado dinamicamente na pipe = "v4/stock-service/dev/terraform.tfstate" 
  }
}
