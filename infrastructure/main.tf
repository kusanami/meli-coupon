data "aws_eks_cluster" "cluster" {
  name = module.eks.cluster_id
}

data "aws_eks_cluster_auth" "cluster" {
  name = module.eks.cluster_id
}

provider "kubernetes" {
  host                   = data.aws_eks_cluster.cluster.endpoint
  cluster_ca_certificate = base64decode(data.aws_eks_cluster.cluster.certificate_authority[0].data)
  token                  = data.aws_eks_cluster_auth.cluster.token
}

module "vpc" {
  source                 = "./vpc"
  name                   = var.name
  environment            = var.environment
  cidr                   = var.cidr
  private_subnets        = var.private_subnets
  public_subnets         = var.public_subnets
  availability_zones     = var.availability_zones
}

module "eks" {
  source                   = "./eks"
  name                     = var.name
  environment              = var.environment
  region                   = var.region
  k8s_version              = var.k8s_version
  vpc_id                   = module.vpc.id
  private_subnets          = module.vpc.private_subnets
  public_subnets           = module.vpc.public_subnets
  kubeconfig_path          = var.kubeconfig_path
  instances_desired_size   = var.instances_desired_size
  instances_max_size       = var.instances_max_size
  instances_min_size       = var.instances_min_size
  instance_type            = var.instance_type
}

module "alb-controller" {
  source                = "./alb-controller"
  name                  = var.name
  environment           = var.environment
  region                = var.region
  vpc_id                = module.vpc.id
  knative_version       = var.knative_version
  cluster_id            = module.eks.cluster_id
  config_path           = module.eks.config_path
  domain                = var.domain
  worker_iam_role       = module.eks.worker_iam_role

  depends_on = [module.eks]
}

module "external-dns" {
  source                = "./external-dns"
  region                = var.region
  cluster_issuer        = module.eks.cluster_issuer

  depends_on = [module.eks]
}

module "autoscaler" {
  source                = "./autoscaler"
  name                  = var.name
  region                = var.region
  environment           = var.environment
  config_path           = module.eks.config_path
  cidr                  = var.cidr
  private_subnets       = var.private_subnets
  public_subnets        = var.public_subnets
  availability_zones    = var.availability_zones
  map_accounts          = var.map_accounts
  map_roles             = var.map_roles
  map_users             = var.map_users
  cluster_id            = module.eks.cluster_id
  worker_iam_role  = module.eks.worker_iam_role

  depends_on = [module.eks]
}

module "coupon-service" {
  source                = "./coupon-service"
  region                = var.region
  config_path           = module.eks.config_path

  depends_on = [module.alb-controller]
}

terraform {
  backend "s3" {

    bucket         = "tfstate-coupon-challenge"
    key            = "global/s3/tfstate-coupon-challenge/terraform.tfstate"
    region         = "us-east-1"
    dynamodb_table = "coupon-challenge"
    encrypt        = true
  }
}
