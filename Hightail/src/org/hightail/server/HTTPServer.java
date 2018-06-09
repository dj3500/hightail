package org.hightail.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executors;
import org.hightail.Config;
import org.hightail.Problem;
import org.hightail.Testcase;
import org.hightail.TestcaseSet;
import org.hightail.util.ProblemNameFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class makes it possible for Hightail to pick up tasks
 * sent via HTTP requests. This makes it possible for browser extensions
 * to parse tasks in the browser and send them to a running Hightail instance.
 *
 * @author Jasper van Merle
 */
public class HTTPServer {
    /**
     * CHelper runs on port 4243. By running Hightail on a different
     * port it's possible to use both tools at the same time.
     */
    public static final int PORT = 4244;

    private HttpServer server = null;
    private ProblemHandler problemHandler = null;

    /**
     * Start the HTTP server and start listening for tasks.
     * If the port is already occupied, it doesn't do anything.
     */
    public void start(ProblemHandler problemHandler) {
        try {
            this.problemHandler = problemHandler;

            server = HttpServer.create(new InetSocketAddress(InetAddress.getByName(null), PORT), 0);
            server.createContext("/", this::handleRequest);
            server.setExecutor(Executors.newSingleThreadExecutor());            
            server.start();
        } catch (IOException ex) {
            // Do nothing
        }
    }

    /**
     * Gracefully stop the server if it's running.
     */
    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    /**
     * Handle a request to the server.
     *
     * Does three things:
     * 1. Check if the request is a POST request.
     * 2. Attempt to parse the body of the request.
     * 3. If successful, add the task with it's tests.
     *
     * @param exchange
     */
    private void handleRequest(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
            // Convert the request body into a string
            InputStream bodyStream = exchange.getRequestBody();
            Scanner sc = new Scanner(bodyStream).useDelimiter("\\A");
            String body = sc.hasNext() ? sc.next() : "";
            sc.close();

            try {
                JSONObject obj = new JSONObject(body);
                Problem problem = jsonToProblem(obj);
                problemHandler.handle(problem);
            } catch (JSONException e) {
                // Do nothing, received data is not JSON or is invalid
            }

            // HTTP 200 - OK
            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().close();
        } else {
            // HTTP 405 - Method Not Allowed
            exchange.sendResponseHeaders(405, 0);
            exchange.getResponseBody().close();
        }
    }

    /**
     * Convert the JSON data to a Problem instance.
     *
     * @param obj
     * @return
     * @throws JSONException
     */
    private Problem jsonToProblem(JSONObject obj) throws JSONException {
        String url = obj.getString("url");

        String name = ProblemNameFormatter.getFormattedName(obj.getString("name"));
        if (name.length() > Problem.PROBLEM_NAME_MAX_LENGTH) {
            name = name.substring(0, Problem.PROBLEM_NAME_MAX_LENGTH);
        }

        if (url.contains("codeforces.com") && !Config.getBoolean("putWholeName")) {
            name = String.valueOf(name.charAt(0));
        }

        int timeLimit = obj.getInt("timeLimit");

        JSONArray testsArr = obj.getJSONArray("tests");
        TestcaseSet testsSet = new TestcaseSet();

        for (int i = 0, iMax = testsArr.length(); i < iMax; i++) {
            JSONObject test = testsArr.getJSONObject(i);

            String input = test.getString("input");
            String output = test.getString("output");

            testsSet.add(new Testcase(input, output, timeLimit));
        }

        return new Problem(name, testsSet, null);
    }
}
