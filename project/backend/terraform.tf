provider "kubernetes" {
  config_path    = "~/.kube/config"
  config_context = "default"
}

resource "kubernetes_namespace" "btv" {
  metadata {
    name = "btv"
  }
}

resource "kubernetes_deployment" "api" {
  metadata {
    name      = "api"
    namespace = "btv"
    labels = {
      app = "api"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "api"
      }
    }

    template {
      metadata {
        labels = {
          "app" = "api"
        }
      }


      spec {
        container {
          image = "registry.btv.mn/api:0.0.2"
          name  = "api"
          env {
            name  = "API_KEY"
            value = null

            value_from {
              secret_key_ref {
                key      = "api"
                name     = "ipinfo"
                optional = false
              }
            }
          }
          env {
            name = "SERVER1"

            value_from {
              config_map_key_ref {
                key  = "server1"
                name = "servers"
              }
            }
          }
          env {
            name = "SERVER2"

            value_from {
              config_map_key_ref {
                key  = "server2"
                name = "servers"
              }
            }
          }
          env {
            name = "DATABASE_URL"

            value_from {
              config_map_key_ref {
                key  = "DATABASE_URL"
                name = "api-config"
              }
            }
          }
          env {
            name = "DATABASE_USER"

            value_from {
              config_map_key_ref {
                key  = "DATABASE_USER"
                name = "api-config"
              }
            }
          }
          env {
            name = "DATABASE_PASSWORD"

            value_from {
              secret_key_ref {
                key  = "DATABASE_PASSWORD"
                name = "api-db-password"
              }
            }
          }
        }
        image_pull_secrets {
          name = "registry-secret"
        }

      }
    }
  }
}

resource "kubernetes_service" "api" {

  metadata {
    labels = {
      "app" = "api"
    }
    name      = "api"
    namespace = "btv"
  }

  spec {
    selector = {
      "app" = "api"
    }
    type = "ClusterIP"

    port {
      port        = 80
      target_port = "8080"
    }
  }
}
