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

import akka.actor.{ Actor, ActorLogging }
import akka.pattern.ask
import org.squbs.unicomplex.{ ReportStatus, StatusReport, Unicomplex }

import scala.concurrent.Await
import scala.concurrent.duration._

case class PrintMessage(txt: String)
case object Explain

class HelloActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case PrintMessage(txt) => printMsg(txt)
    case Explain           => explain()
  }

  private def printMsg(txt: String): Unit = {
    println(s"sdfsdf: $txt")
    log.info(txt)
    sender ! "done"
  }

  private def explain() = {
//    val x: Unicomplex.type = Unicomplex.lookup()
    val getStatus            = Unicomplex() ? ReportStatus
    val status: StatusReport = Await.result(getStatus, 10.seconds).asInstanceOf[StatusReport]
    println(status)
  }
}
