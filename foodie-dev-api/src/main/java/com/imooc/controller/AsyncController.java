package com.imooc.controller;

import com.imooc.aspect.ServiceLogAspect;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * 异步请求和异步调用，测试实现
 * @author Mengdl
 * @date 2021/11/17
 */
@RestController
@RequestMapping(value = "async")
@Api(value = "测试异步请求和异步调用", tags = {"异步请求和异步调用的相关接口"})
public class AsyncController {

    @Resource
    private ThreadPoolTaskExecutor myThreadPoolTaskExecutor;

    public static final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);

    @PostMapping(value = "/email/servletReq")
    @ApiOperation(value = "Servlet方式实现异步请求", notes = "Servlet方式实现异步请求")
    public void servletReq (HttpServletRequest request, HttpServletResponse response) {
        AsyncContext asyncContext = request.startAsync();
        //设置监听器:可设置其开始、完成、异常、超时等事件的回调处理
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                System.out.println("超时了...");
                //做一些超时后的相关操作...
            }
            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                System.out.println("线程开始");
            }
            @Override
            public void onError(AsyncEvent event) throws IOException {
                System.out.println("发生错误："+event.getThrowable());
            }
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                System.out.println("执行完成");
                //这里可以做一些清理资源的操作...
            }
        });
        //设置超时时间
        asyncContext.setTimeout(20000);
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(50000);
                    System.out.println("内部线程：" + Thread.currentThread().getName());
                    asyncContext.getResponse().setCharacterEncoding("utf-8");
                    asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
                    asyncContext.getResponse().getWriter().println("这是异步的请求返回");
                } catch (Exception e) {
                    System.out.println("异常："+e);
                }
                //异步请求完成通知
                //此时整个请求才完成
                asyncContext.complete();
            }
        });
        //此时之类 request的线程连接已经释放了
        System.out.println("主线程：" + Thread.currentThread().getName());
    }

    @GetMapping(value = "/email/callableReq")
    @ApiOperation(value = "使用callable（）", notes = "使用callable（）")
    public Callable<String> callableReq () {
        System.out.println("外部线程：" + Thread.currentThread().getName());
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(10000);
                System.out.println("内部线程：" + Thread.currentThread().getName());
                return "callable!";
            }
        };
    }

    @GetMapping(value = "/email/webAsyncReq")
    public WebAsyncTask<String> webAsyncReq () {
        System.out.println("外部线程：" + Thread.currentThread().getName());
        Callable<String> result = () -> {
            System.out.println("内部线程开始：" + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (Exception e) {
                // TODO: handle exception
            }
            log.info("副线程返回");
            System.out.println("内部线程返回：" + Thread.currentThread().getName());
            return "success";
        };
        //超时处理
        WebAsyncTask<String> wat = new WebAsyncTask<String>(3000L, result);
        wat.onTimeout(new Callable<String>() {
            @Override
            public String call() throws Exception {
                // TODO Auto-generated method stub
                return "超时";
            }
        });
        return wat;
    }


    @RequestMapping(value = "/email/deferredResultReq", method = GET)
    public DeferredResult<String> deferredResultReq () {
        System.out.println("外部线程：" + Thread.currentThread().getName());
        //设置超时时间
        DeferredResult<String> result = new DeferredResult<String>(60*1000L);
        //处理超时事件 采用委托机制
        result.onTimeout(new Runnable() {
            @Override
            public void run() {
                System.out.println("DeferredResult超时");
                result.setResult("超时了!");
            }
        });
        result.onCompletion(new Runnable() {
            @Override
            public void run() {
                //完成后
                System.out.println("调用完成");
            }
        });
        myThreadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //处理业务逻辑
                System.out.println("内部线程：" + Thread.currentThread().getName());
                //返回结果
                result.setResult("DeferredResult!!");
            }
        });
        return result;
    }


    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/email/asyncCall", method = GET)
    public Map<String, Object> asyncCall () {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try{
            //这样调用同类下的异步方法是不起作用的
            //this.testAsyncTask();
            //通过上下文获取自己的代理对象调用异步方法
            AsyncController emailController = (AsyncController)applicationContext.getBean(AsyncController.class);
            emailController.testAsyncTask();
            resMap.put("code",200);
        }catch (Exception e) {
            resMap.put("code",400);
            log.error("error!",e);
        }
        return resMap;
    }

    //注意一定是public,且是非static方法
    @Async
    public void testAsyncTask() throws InterruptedException {
        Thread.sleep(10000);
        System.out.println("异步任务执行完成！");

    }

}
