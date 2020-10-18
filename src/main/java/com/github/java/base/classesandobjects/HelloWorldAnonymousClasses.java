package com.github.java.classesandobjects;

/**
 * Anonymous Classes Example
 *
 * @author pengfei.zhao
 * @date 2020/10/13 16:44
 */
public class HelloWorldAnonymousClasses {
    interface HelloWorld{
        void greet();
        void greetSomeone(String someone);
    }

    public void sayHello(){

        class EnglishGreeting implements HelloWorld{

            @Override
            public void greet() {
                System.out.println("english greet");
            }

            @Override
            public void greetSomeone(String someone) {
                System.out.println("english greet someone");
            }
        }

        HelloWorld englishGreet = new EnglishGreeting();

        HelloWorld frenchGreet = new HelloWorld() {
            @Override
            public void greet() {
                System.out.println("french greet");
            }

            @Override
            public void greetSomeone(String someone) {
                System.out.println("french greet someone");
            }
        };
        HelloWorld chineseGreet = new HelloWorld() {
            @Override
            public void greet() {
                System.out.println("你好");
            }

            @Override
            public void greetSomeone(String someone) {
                System.out.println("你好 某人");
            }
        };
        englishGreet.greet();
        frenchGreet.greet();
        chineseGreet.greet();
    }
}
