/*
 * Copyright 2017 ksilin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.squba

import akka.actor.{ ActorRef, ActorSelection }
import akka.pattern.ask
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import org.squbs.actorregistry.ActorLookup
import org.squbs.unicomplex.RouteDefinition
import io.circe.generic.auto._
import akka.util.Timeout

import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._

class HelloService extends RouteDefinition with FailFastCirceSupport {
  implicit val askTimeout: Timeout = 20.seconds
  override val route: Route = post {
    path("printMsg") {
      entity(as[PrintMessage]) { msg =>
//        val actorRefLkp = ActorLookup("hello_actor") // to useActorLookup, you have to define
        val selectActor: ActorSelection = context.system.actorSelection("user/squba/hello_actor")
        // inst3ead of resolveOne you can also use ! Identify(identifyId)
        val actorRef: ActorRef = Await.result(selectActor.resolveOne, 10.seconds)
        println(s"actorRef: $actorRef, $msg")
        val getResponse = actorRef ? msg
        val response    = Await.result(getResponse, 10.seconds)
        println(s"actor response: $response")
        complete(StatusCodes.OK)
      }
    }
  }
}
