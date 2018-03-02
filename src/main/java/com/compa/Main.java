package com.compa;
//RoutingContext
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;


public class Main extends AbstractVerticle {


    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(Main.class.getName());
    }

    @Override
    public void start() {
        WebClient client = WebClient.create(vertx);


        vertx.createHttpServer()
                .requestHandler(req -> {
                    HttpRequest<Buffer> request = client.get(3081, "localhost", "/");
                    request.send(ar -> {
                        if(ar.succeeded()){

                            String msg = ar.result().bodyAsJsonObject().getString("message");
                            req.response().end();
                        }else{
                            req.response().setStatusCode(500).end();
                        }
                    });
                })
                .listen(8082);
    }



    /*@Override
    public void start() throws Exception {

        System.out.println("Start of Hello World");

        final Router router = Router.router(vertx);

        router.get("/hello/")
                .handler(req -> {

                    System.out.println("Hello World");

                    req.response()
                            .putHeader("content-type", "text/html")
                            .end("<html><body><h1>Hello World</h1></body></html>");
                });

        vertx.createHttpServer() //
                .requestHandler(router::accept) //
                .listen(8080);
    }*/


    /*@Override
    public void start() throws Exception {
        final Router router = Router.router(vertx);



        router.route("/hello")
                .handler(routingContext -> routingContext.response().end("Hello World"));



        router.route("/hi")
                .handler(routingContext -> routingContext.response().end("Hi everybody"));

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8001);
    }*/

}