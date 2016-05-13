#include <stdio.h>
#include <stdlib.h>
#include <time.h>

const char *PATH_GPIO_EXPORT = "/sys/class/gpio/export";
const char *PATH_GPIO_UNEXPORT = "/sys/class/gpio/unexport";
const char *PATH_GPIO_06_DIRECTION = "/sys/class/gpio/gpio6/direction";
const char *PATH_GPIO_06_VALUE = "/sys/class/gpio/gpio6/value";

const char *PATH_GPIO_16_DIRECTION = "/sys/class/gpio/gpio16/direction";
const char *PATH_GPIO_16_VALUE = "/sys/class/gpio/gpio16/value";

#define GPIO_06 6
#define GPIO_16 16

typedef enum {
    LOW = 0,
    HIGH
} gpio_state ;

void gpio_init();
void gpio_exit();
void set_gpio_state(gpio_state state);

FILE *GPIO_EXPORT;
FILE *GPIO_06_DIRECTION;
FILE *GPIO_06_VALUE;
FILE *GPIO_16_DIRECTION;
FILE *GPIO_16_VALUE;

int main()
{
    int time = 0;
    gpio_state state = LOW;
    int touch = 1;

    gpio_init();

/**************** insert your code here ******************/

    set_gpio_state(state);


/*********************************************************/

}

void gpio_init()
{
    if ((GPIO_EXPORT = fopen(PATH_GPIO_EXPORT, "w")) == NULL) {
        printf("%s open failed\n", PATH_GPIO_EXPORT);
        exit(0);
    }

    fprintf(GPIO_EXPORT, "%d", GPIO_06);
    fclose(GPIO_EXPORT);

    if ((GPIO_06_DIRECTION = fopen(PATH_GPIO_06_DIRECTION, "w")) == NULL) {
        printf("%s open failed\n", PATH_GPIO_06_DIRECTION);
        exit(0);
    }
    fprintf(GPIO_06_DIRECTION, "out");
    fclose(GPIO_06_DIRECTION);

    if ((GPIO_06_VALUE = fopen(PATH_GPIO_06_VALUE, "w")) == NULL) {
        printf("%s open failed\n", PATH_GPIO_06_VALUE);
        exit(0);
    }

    if ((GPIO_EXPORT = fopen(PATH_GPIO_EXPORT, "w")) == NULL) {
        printf("%s open failed\n", PATH_GPIO_EXPORT);
        exit(0);
    }

    fprintf(GPIO_EXPORT, "%d", GPIO_16);
    fclose(GPIO_EXPORT);

    if ((GPIO_16_DIRECTION = fopen(PATH_GPIO_16_DIRECTION, "w")) == NULL) {
        printf("%s open failed\n", PATH_GPIO_16_DIRECTION);
        exit(0);
    }
    fprintf(GPIO_16_DIRECTION, "in");
    fclose(GPIO_16_DIRECTION);

    if ((GPIO_16_VALUE = fopen(PATH_GPIO_16_VALUE, "r")) == NULL) {
        printf("%s open failed\n", PATH_GPIO_16_VALUE);
        exit(0);
    }

}
void gpio_exit()
{
    FILE *GPIO_UNEXPORT;    

    fclose(GPIO_06_VALUE);
    fclose(GPIO_16_VALUE);
            
    if ((GPIO_UNEXPORT = fopen(PATH_GPIO_UNEXPORT, "w")) == NULL) {
        printf("%s open failed\n", PATH_GPIO_UNEXPORT);
        exit(0);
    }
    fprintf(GPIO_UNEXPORT, "%d", GPIO_06);
    close(GPIO_UNEXPORT);
}


void set_gpio_state(gpio_state state) 
{
    fprintf(GPIO_06_VALUE, "%d", state);
    fflush(GPIO_06_VALUE);
   /* fprintf(GPIO_16_VALUE, "%d", state);
    fflush(GPIO_16_VALUE);
*/
}

